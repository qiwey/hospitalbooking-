package com.example.project.base.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.DataOutput;

@Getter
@AllArgsConstructor
public enum InsuranceProvider {
    NONE("Không có"),
    HA_NOI("Hà Nội"),
    HA_GIANG("Hà Giang"),
    CAO_BANG("Cao Bằng"),
    BAC_KAN("Bắc Kạn"),
    TUYEN_QUANG("Tuyên Quang"),
    LAO_CAI("Lào Cai"),
    DIEN_BIEN("Điện Biên"),
    LAI_CHAU("Lai Châu"),
    SON_LA("Sơn La"),
    YEN_BAI("Yên Bái"),
    HOA_BINH("Hoà Bình"),
    THAI_NGUYEN("Thái Nguyên"),
    LANG_SON("Lạng Sơn"),
    QUANG_NINH("Quảng Ninh"),
    BAC_GIANG("Bắc Giang"),
    PHU_THO("Phú Thọ"),
    VINH_PHUC("Vĩnh Phúc"),
    BAC_NINH("Bắc Ninh"),
    HAI_DUONG("Hải Dương"),
    HAI_PHONG("Hải Phòng"),
    HUNG_YEN("Hưng Yên"),
    THAI_BINH("Thái Bình"),
    HA_NAM("Hà Nam"),
    NAM_DINH("Nam Định"),
    NINH_BINH("Ninh Bình"),
    THANH_HOA("Thanh Hóa"),
    NGHE_AN("Nghệ An"),
    HA_TINH("Hà Tĩnh"),
    QUANG_BINH("Quảng Bình"),
    QUANG_TRI("Quảng Trị"),
    THUA_THIEN_HUE("Thừa Thiên Huế"),
    DA_NANG("Đà Nẵng"),
    QUANG_NAM("Quảng Nam"),
    QUANG_NGAI("Quảng Ngãi"),
    BINH_DINH("Bình Định"),
    PHU_YEN("Phú Yên"),
    KHANH_HOA("Khánh Hòa"),
    NINH_THUAN("Ninh Thuận"),
    BINH_THUAN("Bình Thuận"),
    KON_TUM("Kon Tum"),
    GIA_LAI("Gia Lai"),
    DAK_LAK("Đắk Lắk"),
    DAK_NONG("Đắk Nông"),
    LAM_DONG("Lâm Đồng"),
    BINH_PHUOC("Bình Phước"),
    TAY_NINH("Tây Ninh"),
    BINH_DUONG("Bình Dương"),
    DONG_NAI("Đồng Nai"),
    BA_RIA_VUNG_TAU("Bà Rịa - Vũng Tàu"),
    HO_CHI_MINH("Hồ Chí Minh"),
    LONG_AN("Long An"),
    TIEN_GIANG("Tiền Giang"),
    BEN_TRE("Bến Tre"),
    TRA_VINH("Trà Vinh"),
    VINH_LONG("Vĩnh Long"),
    DONG_THAP("Đồng Tháp"),
    AN_GIANG("An Giang"),
    KIEN_GIANG("Kiên Giang"),
    CAN_THO("Cần Thơ"),
    HAU_GIANG("Hậu Giang"),
    SOC_TRANG("Sóc Trăng"),
    BAC_LIEU("Bạc Liêu"),
    CA_MAU("Cà Mau");

    private final String value;
//    private final Double discount;
}
