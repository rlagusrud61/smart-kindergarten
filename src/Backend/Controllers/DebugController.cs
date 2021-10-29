using KindergartenApi.Context;
using KindergartenApi.Hubs;
using KindergartenApi.Models.DB;
using KindergartenApi.Models.DTO;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class DebugController : ControllerBase
{
    private readonly IHubContext<ActivityHub, IActivityHub> _activityHub;
    private readonly IHubContext<StudentHub, IStudentHub> _studentHub;
    private readonly GartenContext _context;

    public DebugController(IHubContext<ActivityHub, IActivityHub> activityHub,
        IHubContext<StudentHub, IStudentHub> studentHub, GartenContext context)
    {
        _activityHub = activityHub;
        _studentHub = studentHub;
        _context = context;
    }

    [HttpPut("AddEvent")]
    public async Task<IActionResult> AddEvent(Guid studentId, UrgentEvent @event)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.Id == studentId);
        if (student is null)
            return BadRequest();

        await _activityHub.Clients.All.ReceiveUrgentEvent(new EventHistory {Student = student, Event = @event});
        return Ok();
    }
    
    [HttpPut("SetVocalActivity")]
    public async Task<IActionResult> SetVocalActivity(Guid studentId, UrgentEvent @event)
    {
        throw new NotImplementedException();
    }
    
    [HttpPut("SetActivity")]
    public async Task<IActionResult> SetActivity(Guid studentId, UrgentEvent @event)
    {
        throw new NotImplementedException();
    }
}