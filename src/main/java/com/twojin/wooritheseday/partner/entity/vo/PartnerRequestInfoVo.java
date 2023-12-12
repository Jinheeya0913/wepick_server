package com.twojin.wooritheseday.partner.entity.vo;


import com.twojin.wooritheseday.partner.entity.PartnerRequestQueueDTO;
import com.twojin.wooritheseday.user.entity.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PartnerRequestInfoVo {

    PartnerRequestQueueDTO partnerRequestQueueDTO;
    UserDTO userDTO;
}
