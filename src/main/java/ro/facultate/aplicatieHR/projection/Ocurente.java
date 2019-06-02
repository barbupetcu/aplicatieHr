package ro.facultate.aplicatieHR.projection;

import ro.facultate.aplicatieHR.entity.dic.DicContract;

import java.util.Date;

public interface Ocurente {
    Date getDateEff();
    Date getEndDate();
    DicContract getContract();

    interface DicContract{
        Long getId();
    }

}
