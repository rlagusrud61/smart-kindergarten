using KindergartenApi.Context;
using KindergartenApi.Hubs;
using KindergartenApi.Models.DB;
using KindergartenApi.Models.DTO;
using KindergartenApi.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class BluetoothController : ControllerBase
{
    private readonly ILogger<BluetoothController> Logger;
    private readonly GartenContext _context;
    private readonly IHubContext<ProximityHub, IProximityHub> _hubContext;
    private readonly HistoryService _history;

    public BluetoothController(ILogger<BluetoothController> logger, GartenContext context,
        IHubContext<ProximityHub, IProximityHub> hubContext, HistoryService history)
    {
        Logger = logger;
        _context = context;
        _hubContext = hubContext;
        _history = history;
    }

    [HttpPut("{hardwareAddress}")]
    public async Task<IActionResult> UpdateNearbyDevices(string hardwareAddress, List<NearbyBluetooth> nearbyDevices) // very secure yesyes
    {
        Logger.LogInformation("Received bluetooth update from {HardwareAddress}: {NearbyDevices}", hardwareAddress,
            string.Join('\n', nearbyDevices.Select(m => $"Device {m.HardwareAddress} ({m.Name}): RSSI {m.Rssi}")));

        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == hardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");

        // var previousDevices = context get recent
        // var previousTimestamp
        var previousDevices = _history.StudentProximity.ContainsKey(student.Id)
            ? _history.StudentProximity[student.Id]
            : null;

        _history.StudentProximity[student.Id] = nearbyDevices; // Update history with new entry

        if (previousDevices is null) return BadRequest();


        var actuallyMutuallyNearbyStudents = new List<Student>();
        // compare whether all our nearby connections (bigger than some rssi threshold) are also mutually nearby us
        // Add those that are to our nearby group
        foreach (var nearby in nearbyDevices.Where(m => m.Rssi >= -50))
        {
            var nearbyStudent =
                await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == nearby.HardwareAddress);
            if (nearbyStudent is null) continue;

            var nearbyStudentsProximity = _history.StudentProximity.ContainsKey(nearbyStudent.Id)
                ? _history.StudentProximity[nearbyStudent.Id]
                : null;
            if (nearbyStudentsProximity is null) continue;
            if (nearbyStudentsProximity.Any(m => m.Rssi < -50)) continue;
            // Both are nearby

            actuallyMutuallyNearbyStudents.Add(nearbyStudent);
        }


        // See which devices have been around since last time
        // and add the passed duration to the nearby timer for the device pairs


        // Additionally stream updated groups to frontend

        // Note: while there will likely be groups with all mutual connections, not everyone within the group has to be connected only to this group,
        // they could branch of to other nearby groups. The visualisation would be most representative as a tree/web/nodes

        await _hubContext.Clients.All.UpdateDeviceProximity(hardwareAddress, nearbyDevices);

        return Ok();
    }

    [HttpGet("StudentProximities")]
    public async Task<ActionResult<IEnumerable<NearbyBluetooth>>> GetStudentNearbyDevices(string hardwareAddress)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == hardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        return Ok(_history.StudentProximity[student.Id]);
    }

    [HttpGet("AllProximities")]
    public ActionResult<Dictionary<string, IEnumerable<NearbyBluetooth>>> GetEveryonesNearbyDevices()
    {
        return Ok(_history.StudentProximity);
    }
}