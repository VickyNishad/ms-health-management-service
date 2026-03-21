/**
 * 
 */
package com.health.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.health.dto.response.DoctorClinicAvailabilityDto;
import com.health.dto.response.SlotSummary;
import com.health.entity.Doctor;
import com.health.entity.DoctorSlot;
import com.health.entity.UserRegistration;
import com.health.repository.DoctorRepository;
import com.health.repository.DoctorSlotRepository;
import com.health.repository.UserRegistrationRepository;
import com.health.service.DoctorService;
import com.health.utility.ApiExecutionUtils;
import com.health.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.health.dto.MessageResponse;
import com.health.models.ApiResponse;
import com.health.service.SlotService;

import jakarta.transaction.Transactional;

/**
 * 
 */
@Service
@Transactional
public class SlotServiceImpl implements SlotService {

	@Autowired
	private UserRegistrationRepository userRegistrationRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private DoctorSlotRepository doctorSlotRepository;

	@Autowired
	private DoctorService doctorService;

	@Override
	public ApiResponse<MessageResponse> generateNext7DaysSlots(Long userId,Long doctorId) {
		// TODO Auto-generated method stub

		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{
			       // check user active or register
					Optional<UserRegistration> userRegistration = userRegistrationRepository.findById(userId);
					if(userRegistration.isEmpty()) {
						throw new RuntimeException("User not found");
					}

					// check doctor active or created
					Optional<Doctor> doctor = doctorRepository.findById(doctorId);
					if(doctor.isEmpty()) {
						throw new RuntimeException("Doctor not found");
					}

					// check doctor available or not
					Long doctor_user_id = doctor.get().getId();
					ApiResponse<List<DoctorClinicAvailabilityDto>> apiResponse = doctorService.getAvailability(doctor_user_id);
					if (!apiResponse.isSuccess()) {
						throw new RuntimeException(apiResponse.getMessage());
					}
					// get doctor clinic availability
					List<DoctorClinicAvailabilityDto> doctorClinicAvailabilityDtos = apiResponse.getData();
					for (DoctorClinicAvailabilityDto dto : doctorClinicAvailabilityDtos) {

						// first check slot available for same date and time
						// if return false then generate slot
						LocalDate date = DateUtils.getNextOrSame(dto.getDayOfWeek());
						System.out.println("date: " + date);
						DayOfWeek dayOfWeek = dto.getDayOfWeek();
						LocalTime startTime = dto.getStartTime();
						LocalTime endTime = dto.getEndTime();
						Long clinic_id = dto.getClinicId();

						boolean existSlot = doctorSlotRepository.existsByDoctorIdAndClinicIdAndSlotDateAndSlotStartTimeAndSlotEndTime(doctorId, clinic_id, date, startTime, endTime);
						if(!existSlot) {

							// generate slot
							DoctorSlot doctorSlot = new DoctorSlot();
							doctorSlot.setDoctorId(doctorId);
							doctorSlot.setClinicId(clinic_id);
							doctorSlot.setSlotDate(date);
							doctorSlot.setSlotStartTime(startTime);
							doctorSlot.setSlotEndTime(endTime);
							doctorSlot.setCreatedAt(LocalDateTime.now());
							doctorSlot.setCreatedBy(userId.toString());
							doctorSlotRepository.save(doctorSlot);
						} else {
							System.out.println("------------------------");
							System.out.println("existSlot: " + existSlot);
							System.out.println("date: " + date);
						}

					}

					return new MessageResponse("Slot generated successfully");
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<List<SlotSummary>> getSlots(Long doctorId, Long clinicId, LocalDate date) {
		// TODO Auto-generated method stub
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{
					// check doctor active or created
					Optional<Doctor> doctor = doctorRepository.findById(doctorId);
					if(doctor.isEmpty()) {
						throw new RuntimeException("Doctor not found");
					}
					System.out.println("date : "+date);
					List<DoctorSlot> doctorSlots = doctorSlotRepository.findByDoctorIdAndClinicIdAndSlotDate(doctorId, clinicId, date);
					return doctorSlots.stream().map(s -> new SlotSummary(
							s.getId(),
							s.getSlotDate(),
							s.getSlotStartTime(),
							s.getSlotEndTime(),
							s.getStatus()
					)).collect(Collectors.toList());
				},
				ApiResponse::success);
	}

	@Override
	public ApiResponse<List<SlotSummary>> getSlotSummary(Long doctorId, Long clinicId) {
		// TODO Auto-generated method stub
		return getListApiResponse(doctorId, clinicId);
	}

	@Override
	public ApiResponse<List<SlotSummary>> getSlotsByDoctorClinicAndDate(Long doctorId, Long clinicId,
			LocalDate date) {
		// TODO Auto-generated method stub
		return getListApiResponse(doctorId, clinicId);
	}

	private ApiResponse<List<SlotSummary>> getListApiResponse(Long doctorId, Long clinicId) {
		return ApiExecutionUtils.ApiExecutor.processRequest(null,
				req ->{},
				()->{

					// check doctor active or created
					Optional<Doctor> doctor = doctorRepository.findById(doctorId);
					if(doctor.isEmpty()) {
						throw new RuntimeException("Doctor not found");
					}
					LocalDate today = LocalDate.now();
					System.out.println("date : "+today);
					List<DoctorSlot> slots = doctorSlotRepository
							.findByDoctorIdAndClinicIdAndSlotDateGreaterThanEqualAndIsBookedFalse(
									doctorId, clinicId, today);
					return slots.stream().map(s -> new SlotSummary(
							s.getId(),
							s.getSlotDate(),
							s.getSlotStartTime(),
							s.getSlotEndTime(),
							s.getStatus()
					)).collect(Collectors.toList());
				},
				ApiResponse::success);
	}

//	@Override
//	public ResponseEntity<ApiResponse<MessageResponse>> generateNext7DaysSlots(Long doctorId, Long clinicId) {
//		// TODO Auto-generated method stub
//
//		ApiResponse<MessageResponse> success = ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
//			if (doctorId == null || clinicId == null) {
//				throw new RuntimeException("Doctor ID and Clinic ID are required");
//			}
//			
//		}, () -> {
//
//			LocalDate today = LocalDate.now();
//
//			// ========== 1. Delete only free future slots ==========
//			japDoctorSlotRepository.deleteOnlyFreeFutureSlots(doctorId, clinicId, today);
//
//			int limit = 7;
//
//			for (int i = 0; i < limit; i++) {
//
//				LocalDate date = today.plusDays(i);
//				String day = date.getDayOfWeek().name();
//
//				// ========== 2. Availability Check ==========
//				List<DoctorAvailability> availList = doctorAvailabilityRepository.findByDoctorClinicAndDay(doctorId,
//						clinicId, day);
//
//				if (availList.isEmpty())
//					continue;
//
//				// ========== 3. Leave / Break / Lunch ==========
//				List<DoctorLeave> leaves = doctorLeaveRepository.findByDoctorAndDate(doctorId, date);
//
//				// Get next token AFTER booked appointments
//				int token = Optional.ofNullable(japDoctorSlotRepository.getMaxTokenForDate(doctorId, clinicId, date))
//						.orElse(0) + 1;
//
//				// Generate slots for each availability window
//				for (DoctorAvailability av : availList) {
//
//					LocalTime slotStart = LocalTime.parse(av.getStartTime());
//					LocalTime end = LocalTime.parse(av.getEndTime());
//					int duration = av.getSlotDuration();
//
//					while (!slotStart.plusMinutes(duration).isAfter(end)) {
//
//						LocalTime nextStart = slotStart.plusMinutes(duration);
//						String status = "AVAILABLE";
//
//						// FULL DAY LEAVE
//						if (leaves.stream().anyMatch(l -> "FULL_DAY".equalsIgnoreCase(l.getLeaveType()))) {
//							status = "LEAVE";
//						} else {
//							// Time-based leave handling
//							for (DoctorLeave leave : leaves) {
//								boolean inRange = !slotStart.isBefore(leave.getStartTime())
//										&& slotStart.isBefore(leave.getEndTime());
//
//								if (inRange) {
//									switch (leave.getLeaveType().toUpperCase()) {
//									case "BREAK":
//										status = "BREAK";
//										break;
//									case "LUNCH":
//										status = "LUNCH";
//										break;
//									default:
//										status = "LEAVE";
//									}
//								}
//							}
//						}
//
//						// Save Slot
//						DoctorSlot slot = new DoctorSlot();
//						slot.setDoctorId(doctorId);
//						slot.setClinicId(clinicId);
//						slot.setSlotDate(date);
//						slot.setSlotTime(slotStart);
//						slot.setToken(token++);
//						slot.setStatus(status);
//						slot.setIsBooked(false);
//						slot.setCreatedAt(LocalDateTime.now());
//						slot.setCreatedBy(doctorId.toString());
//						japDoctorSlotRepository.save(slot);
//
//						slotStart = nextStart;
//					}
//				}
//			}
//			return new MessageResponse("Slots generated successfully");
//		}, ApiResponse::success);
//
//		return new ResponseEntity<ApiResponse<MessageResponse>>(success, HttpStatus.OK);
//	}
//
//	@Override
//	public void getSlots(Long doctorId, Long clinicId, LocalDate date) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public ResponseEntity<ApiResponse<List<SlotSummaryDTO>>> getSlotSummary(Long doctorId, Long clinicId) {
//		// TODO Auto-generated method stub
//
//		ApiResponse<List<SlotSummaryDTO>> success = ApiExecutionUtils.ApiExecutor.processRequest(null, req -> {
//		}, () -> {
//			List<DoctorSlot> slots = japDoctorSlotRepository.findByDoctorAndClinic(doctorId, clinicId);
//
//			Map<LocalDate, List<DoctorSlot>> groupedByDate = slots.stream()
//					.collect(Collectors.groupingBy(DoctorSlot::getSlotDate));
//
//			List<SlotSummaryDTO> summaryList = new ArrayList<>();
//
//			for (Map.Entry<LocalDate, List<DoctorSlot>> entry : groupedByDate.entrySet()) {
//				LocalDate date = entry.getKey();
//				List<DoctorSlot> daySlots = entry.getValue();
//
//				long total = daySlots.size();
//				long available = daySlots.stream().filter(s -> "AVAILABLE".equalsIgnoreCase(s.getStatus())).count();
//				long leave = daySlots.stream().filter(s -> "LEAVE".equalsIgnoreCase(s.getStatus())).count();
//
//				summaryList.add(new SlotSummaryDTO(date, total, available, leave));
//			}
//
//			// Sort by date ascending
//			summaryList.sort(Comparator.comparing(SlotSummaryDTO::getSlotDate));
//			return summaryList;
//		}, ApiResponse::success);
//
//		return new ResponseEntity<ApiResponse<List<SlotSummaryDTO>>>(success, HttpStatus.OK);
//	}
//
//	@Override
//	public ResponseEntity<ApiResponse<List<SlotDTO>>> getSlotsByDoctorClinicAndDate(Long doctorId, Long clinicId,
//			LocalDate date) {
//		// TODO Auto-generated method stub
//
//		ApiResponse<List<SlotDTO>> success = ApiExecutionUtils.ApiExecutor.processRequest(null, null, () -> {
//			try {
//				updateSlotsForSameDayChanges(doctorId, clinicId, date);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			List<DoctorSlot> slots = japDoctorSlotRepository.findSlotsByDoctorClinicAndDate(doctorId, clinicId, date);
//
//			return slots.stream().map(s -> new SlotDTO(s.getSlotId(),s.getSlotTime(), s.getToken(), s.getStatus(), s.getIsBooked()))
//					.collect(Collectors.toList());
//		}, ApiResponse::success);
//
//		return new ResponseEntity<ApiResponse<List<SlotDTO>>>(success, HttpStatus.OK);
//	}
//	
//	
//	
//	@Transactional
//	public void updateSlotsForSameDayChanges(Long doctorId, Long clinicId, LocalDate date) {
//
//	    if (doctorId == null || clinicId == null) {
//	        throw new RuntimeException("Doctor ID and Clinic ID are required.");
//	    }
//
//	    if (date == null) {
//	        throw new RuntimeException("Date is mandatory to update slot status.");
//	    }
//
//	    // 1. Fetch slots
//	    List<DoctorSlot> slots =
//	            japDoctorSlotRepository.findByDoctorIdAndClinicIdAndSlotDate(doctorId, clinicId, date);
//
//	    if (slots.isEmpty()) {
//	        throw new RuntimeException("No slots found for the selected doctor, clinic and date.");
//	    }
//
//	    // 2. Fetch break/lunch/leave records
//	    List<DoctorLeave> leaves =
//	            doctorLeaveRepository.findByDoctorAndDate(doctorId, date);
//
//	    if (leaves == null || leaves.isEmpty()) {
//	        // Nothing to update → leave existing slots untouched
//	        return;
//	    }
//
//	    // 3. FULL DAY LEAVE → set all non-booked slots to LEAVE
//	    boolean fullDayLeave = leaves.stream()
//	            .anyMatch(l -> "FULL_DAY".equalsIgnoreCase(l.getLeaveType()));
//
//	    if (fullDayLeave) {
//
//	        for (DoctorSlot slot : slots) {
//	            if (!slot.getIsBooked()) {
//	                slot.setStatus("LEAVE");
//	            }
//	        }
//
//	        japDoctorSlotRepository.saveAll(slots);
//	        return;
//	    }
//
//	    // 4. Time-based break/lunch/partial leave
//	    for (DoctorSlot slot : slots) {
//
//	        if (slot.getIsBooked()) {
//	            continue;  // NEVER touch booked slots
//	        }
//
//	        String finalStatus = "AVAILABLE";
//
//	        for (DoctorLeave leave : leaves) {
//
//	            // Validation: START / END required for time-based leave
//	            if ("BREAK".equalsIgnoreCase(leave.getLeaveType()) ||
//	                "LUNCH".equalsIgnoreCase(leave.getLeaveType()) ||
//	                "LEAVE".equalsIgnoreCase(leave.getLeaveType())) {
//
//	                if (leave.getStartTime() == null || leave.getEndTime() == null) {
//	                    throw new RuntimeException("Start time and end time are mandatory for "
//	                            + leave.getLeaveType() + " on " + date);
//	                }
//
//	                if (!leave.getStartTime().isBefore(leave.getEndTime())) {
//	                    throw new RuntimeException("Invalid time range for "
//	                            + leave.getLeaveType() + " on " + date);
//	                }
//	            }
//
//	            // Apply logic only if slot inside the time range
//	            boolean inRange =
//	                    leave.getStartTime() != null &&
//	                    leave.getEndTime() != null &&
//	                    !slot.getSlotTime().isBefore(leave.getStartTime()) &&
//	                     slot.getSlotTime().isBefore(leave.getEndTime());
//
//	            if (inRange) {
//	                finalStatus = switch (leave.getLeaveType().toUpperCase()) {
//	                    case "BREAK" -> "BREAK";
//	                    case "LUNCH" -> "LUNCH";
//	                    default -> "LEAVE";
//	                };
//	            }
//	        }
//
//	        slot.setUpdatedAt(LocalDateTime.now());
//	        slot.setUpdatedBy(doctorId.toString());
//	        slot.setStatus(finalStatus);
//	    }
//
//	    japDoctorSlotRepository.saveAll(slots);
//	}

}
