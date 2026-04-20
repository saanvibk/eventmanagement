//Get Event Details to populate the report

package structural.facade;

public class EventDetailsFacade implements PaymentFacade {

    public Details getDetails()
    {
        EventDetails ed = new EventDetails();
        return ed;
    }
}