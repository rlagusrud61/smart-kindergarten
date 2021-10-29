using KindergartenApi.Models.DB;
using Microsoft.AspNetCore.SignalR;

namespace KindergartenApi.Hubs;

public interface IActivityHub
{
    Task ReceiveStudentEvent(Student student, UrgentEvent @event);
}

public class ActivityHub : Hub<IActivityHub>
{
    public async Task SendEvent(Student student, UrgentEvent @event)
    {
        await Clients.All.ReceiveStudentEvent(student, @event);
    }
}