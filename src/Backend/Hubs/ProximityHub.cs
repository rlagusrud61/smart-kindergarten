using KindergartenApi.Models.DB;
using KindergartenApi.Models.DTO;
using Microsoft.AspNetCore.SignalR;

namespace KindergartenApi.Hubs;

public interface IProximityHub
{
    Task UpdateDeviceProximity(Guid studentId, IEnumerable<Guid> nearbyDevices);
}

public class ProximityHub : Hub<IProximityHub>
{
    public async Task SendUpdatedGroups(Guid studentId, IEnumerable<Guid> nearbyDevices)
    {
        await Clients.All.UpdateDeviceProximity(studentId, nearbyDevices);
    }
}