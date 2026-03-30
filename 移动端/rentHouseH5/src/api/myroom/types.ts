export interface GraphVo {
  name: string;
  url: string;
}

export interface MyRoomInfoVo {
  id: number;
  apartmentName: string;
  apartmentAddress: string;
  graphVoList: GraphVo[];
  leaseStatus: number;
  leaseStartDate: string;
  leaseEndDate: string;
  roomNumber: string;
  rent: number;
}

export interface RepairSubmitVo {
  roomId?: number;
  type?: number;
  description: string;
  contactPhone: string;
  appointmentTime: string; // Date string
  images: string[];
}

export interface RepairRecordVo {
  id: number;
  type: string;
  description: string;
  contactPhone: string;
  appointmentTime: string;
  status: number; // 1-待处理, 2-处理中, 3-已完成
  images: string; // JSON Array String
  createTime: string;
}

export interface PaymentRecordVo {
  id: number;
  paymentType: string;
  amount: number;
  status: number; // 0-未支付, 1-已支付
  paymentTime: string;
  createTime: string;
}

export interface PageResponse<T> {
  records: T[];
  total: number;
  size: number;
  current: number;
}
