package portfolio.dattq.payload.request;

import lombok.Data;

@Data
public class ContactRequest {
    private String type;
    private String label;
    private String url;
    private String icon;
}
