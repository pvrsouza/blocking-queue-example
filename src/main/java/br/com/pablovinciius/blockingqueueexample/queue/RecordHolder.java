package br.com.pablovinciius.blockingqueueexample.queue;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class RecordHolder<T extends Transaction> {

    private String name;

    private String recordId;

    private LocalDateTime timestamp = LocalDateTime.now();

    private T record;

    public static <T extends Transaction> RecordHolder<T> of(String name, String recordId, T record) {
        RecordHolder<T> recordHolder = new RecordHolder<>();
        recordHolder.setRecord(record);
        recordHolder.setName(name);
        recordHolder.setRecordId(recordId);
        return recordHolder;
    }

    public Integer convertAccountToInteger(){
        int sum = 0;
        for( final Character letter : record.getAccount().toCharArray()){
            sum += (int) letter;
        }

        return sum;
    }
}
