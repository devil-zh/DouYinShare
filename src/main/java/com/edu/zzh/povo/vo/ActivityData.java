package com.edu.zzh.povo.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: zzh
 * @Date: 2021/3/18 11:31
 * @Description:
 */
@Data
public class ActivityData {
    Integer users;
    Integer totalCards;
    Integer usedCards;
    Integer unusedlCards;
}
