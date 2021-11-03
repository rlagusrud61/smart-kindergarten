using KindergartenApi.Models.DB;
using KindergartenApi.Models.DTO;

namespace KindergartenApi.Services;

public class HistoryService
{
    // Don't really mind concurrency details
    public Dictionary<Guid, Activity> StudentActivity { get; } = new();
    public Dictionary<Guid, VocalActivity> StudentVocalActivity { get; } = new();
    public Dictionary<Guid, IEnumerable<NearbyBluetooth>> StudentProximity { get; } = new();
}