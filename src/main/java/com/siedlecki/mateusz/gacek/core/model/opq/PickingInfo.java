package com.siedlecki.mateusz.gacek.core.model.opq;

import com.siedlecki.mateusz.gacek.core.Constants;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PickingInfo {
    private int qtyToOrder = 0;
    private int qtyAfterDay = 0;
    private int noInfo = 0;
    private int qtyReservationInCps = 0;
    private int qtyReservationInFps = 0;

    private final int daysToPick;

    private final LocalDate date;

    public PickingInfo(Pick pick, int daysToPick) {
        this.daysToPick = daysToPick;
        date = LocalDate.now().plusDays(daysToPick);
        addPick(pick);
    }

    public void addPick(Pick pick){
        if (pick.getCutOffDate()!=null && pick.getCutOffTime()!=null) {
            if (pick.getCutOffDate().isAfter(date)) {
                qtyAfterDay+=pick.getPickqty();
            } else {
                qtyToOrder+=pick.getPickqty();
                if (Constants.CPS_CUT_OF_TIME.equals(pick.getCutOffTime())) {
                    qtyReservationInCps+=pick.getPickqty();
                }else {
                    qtyReservationInFps+=pick.getPickqty();
                }
            }
        }else {
            noInfo+=pick.getPickqty();
            qtyReservationInCps+=pick.getPickqty();
        }
    }

    @Override
    public String toString() {
        return ", qtyToOrder=" + qtyToOrder +
               ", qtyAfterDay=" + qtyAfterDay +
               ", qtyReservationInCps=" + qtyReservationInCps +
               ", qtyReservationInFps=" + qtyReservationInFps +
               ", date=" + date;
    }
}
