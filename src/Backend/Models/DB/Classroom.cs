using System.ComponentModel.DataAnnotations;

namespace KindergartenApi.Models.DB;

public class Classroom
{
    public Classroom(Guid id, string roomIdentifier)
    {
        RoomIdentifier = roomIdentifier;
    }

    public Guid Id { get; set; }
    [Required]
    public string RoomIdentifier { get; set; }
}
