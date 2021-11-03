using KindergartenApi.Models.DB;
using KindergartenApi.Models.DTO;
using Microsoft.AspNetCore.SignalR;

namespace KindergartenApi.Hubs;

public interface IProximityHub
{
    Task UpdateDeviceProximity(string ownerHardwareAddress, IEnumerable<NearbyBluetooth> nearbyDevices);
}

public class ProximityHub : Hub<IProximityHub>
{
    public async Task SendUpdatedGroups(string ownerHardwareAddress, IEnumerable<NearbyBluetooth> nearbyDevices)
    {
        await Clients.All.UpdateDeviceProximity(ownerHardwareAddress, nearbyDevices);
    }
}