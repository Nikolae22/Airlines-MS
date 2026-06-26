package com.seatservice.service.impl;

import com.enums.SeatType;
import com.payload.request.SeatRequest;
import com.payload.response.SeatResponse;
import com.seatservice.mapper.SeatMapper;
import com.seatservice.model.Seat;
import com.seatservice.model.SeatMap;
import com.seatservice.repository.SeatMapRepository;
import com.seatservice.repository.SeatRepository;
import com.seatservice.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapRepository seatMapRepository;

    @Override
    public void generateSeat(Long setMapId) throws Exception {
        boolean exists=seatRepository.existsBySeatMapId(setMapId);
        if (exists){
            throw new Exception("Seats already created for seat map");
        }

        SeatMap seatMap=seatMapRepository.findById(setMapId)
                .orElseThrow(()->new Exception("Seat map not found"));

        int leftSeatsPerRow=seatMap.getLeftSeatsPerRow();
        int rightSeatsPerRow=seatMap.getRightSeatsPerRow();
        int rows=seatMap.getTotalRows();
        int seatPerRow=leftSeatsPerRow+rightSeatsPerRow;
        List<Seat> seats=new ArrayList<>();

        for (int row=1;row<=rows;row++){
            for (int col=0;col<seatPerRow; col++){
              String seatNum=row+getSeatLetter(col);
                SeatType type=getSeatType(col,leftSeatsPerRow,rightSeatsPerRow);
                Seat seat=Seat.builder()
                        .seatNumber(seatNum)
                        .seatRow(row)
                        .columnLetter(getSeatLetter(col).charAt(0))
                        .seatType(type)
                        .seatMap(seatMap)
                        .build();

                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);
    }

    private SeatType getSeatType(int col, int leftSeatsPerRow, int rightSeatsPerRow) {

        int totalSeats=leftSeatsPerRow+rightSeatsPerRow;
        //windows
        //la prima e lultima seatse perfoeza vicino alla finestra
        if (col==0 || col==totalSeats -1) return SeatType.WINDOWS;

        //asile sedia vicino al centro del aereo sia destra che sinistra
        //left asile
        if (col==leftSeatsPerRow -1) return SeatType.AISLE;
//        right aisle
        if (col==leftSeatsPerRow) return SeatType.AISLE;
        return SeatType.MIDDLE;
    }

    //0=A 1=B 2=c 25=z
    //col=27
    //27/26=1=B
    // sb="B"
    // step 2
    // col=27/26-1= col =1-1= col=0
    //0%26=0=A
    //sb="AB"
    //col=0%26-1 = col= 0-1=-1
    private String getSeatLetter(int col) {
        StringBuilder sb=new StringBuilder();
        while (col>=0){
            //26 perche abbiamo solo 25 letter
            sb.insert(0,(char)('A'+(col%26)));
            col=col/26-1;
        }
        return sb.toString();
    }

    @Override
    public List<SeatResponse> getAll() {
        return seatRepository.findAll()
                .stream()
                .map(SeatMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SeatResponse updateSeats(Long seatId, SeatRequest request) {
        return null;
    }
}
