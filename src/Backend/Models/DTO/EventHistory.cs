using KindergartenApi.Models.DB;

namespace KindergartenApi.Models.DTO;

public class EventHistory
{
    public Student Student { get; set; }
    public UrgentEvent Event { get; set; }
    public DateTime Date { get; set; } = DateTime.UtcNow;
}