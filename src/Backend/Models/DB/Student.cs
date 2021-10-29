using System.ComponentModel.DataAnnotations;

namespace KindergartenApi.Models.DB;

public class Student
{
    public Guid Id { get; set; }
    /// <summary>
    /// The MAC address for this child's wearable
    /// </summary>
    public string? DeviceHardwareAddress { get; set; }

    [Required]
    public string Name { get; set; }

    [Required]
    public int Age { get; set; }
    public IEnumerable<Teacher> Teachers { get; set; }
}
