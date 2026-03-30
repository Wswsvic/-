import http from "@/utils/http";
import type {
  MyRoomInfoVo,
  RepairSubmitVo,
  RepairRecordVo,
  PaymentRecordVo,
  PageResponse
} from "./types";

export function getMyRoomInfo() {
  return http.get<MyRoomInfoVo>("/app/myroom/info");
}

export function submitRepairStatus(data: RepairSubmitVo) {
  return http.post<number>("/app/myroom/repair/save", data);
}

export function getRepairList(params: { current: number; size: number }) {
  return http.get<PageResponse<RepairRecordVo>>(
    "/app/myroom/repair/list",
    params
  );
}

export function getPaymentList(params: { current: number; size: number }) {
  return http.get<PageResponse<PaymentRecordVo>>(
    "/app/myroom/payment/list",
    params
  );
}

export function getUnpaidTotal() {
  return http.get<number>("/app/myroom/payment/unpaid/total");
}
