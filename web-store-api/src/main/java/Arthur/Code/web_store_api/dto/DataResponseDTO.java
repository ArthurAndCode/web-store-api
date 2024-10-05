package Arthur.Code.web_store_api.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class DataResponseDTO<T> {
    private Map<String, List<T>> itemsMap = new HashMap<>();

    public DataResponseDTO(List<T> items, String fieldName) {
        this.itemsMap.put(fieldName, items);
    }

    @JsonAnyGetter
    public Map<String, List<T>> getItemsMap() {
        return itemsMap;
    }
}

