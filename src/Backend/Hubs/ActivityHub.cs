using KindergartenApi.Models.DB;
using KindergartenApi.Models.DB.Activity;
using KindergartenApi.Models.DTO;
using Microsoft.AspNetCore.SignalR;

namespace KindergartenApi.Hubs;

public interface IActivityHub
{
    Task ReceiveUrgentEvent(EventHistory eventHistory);
}

public class ActivityHub : Hub<IActivityHub>
{
    public async Task SendEvent(Student student, UrgentEvent @event)
    {
        await Clients.All.ReceiveUrgentEvent(new EventHistory() { Student = student, Event = @event });
    }
}