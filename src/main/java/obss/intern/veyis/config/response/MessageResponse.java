package obss.intern.veyis.config.response;


import lombok.AllArgsConstructor;

/**
 * This Response is returned upon Post-Put-Delete requests to inform the client.
 */
@AllArgsConstructor
public class MessageResponse {
    public final String message;
    public final MessageType messageType;
}
