namespace KindergartenApi.Models.DB;

public class Student
{
    public Guid Id { get; set; }
    /// <summary>
    /// The MAC address for this child's wearable
    /// </summary>
    public string? DeviceHardwareAddress { get; set; }

    public string Name { get; set; }
    public IEnumerable<Teacher> Teachers { get; set; }
}
