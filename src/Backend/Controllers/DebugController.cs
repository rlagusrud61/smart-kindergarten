using KindergartenApi.Context;
using KindergartenApi.Hubs;
using KindergartenApi.Models.DB;
using KindergartenApi.Models.DB.Activity;
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

        await _activityHub.Clients.All.ReceiveUrgentEvent(new EventHistory { Student = student, Event = @event });
        return Ok();
    }

    [HttpPost("AddStudent", Name = "AddStudent")]
    public async Task<IActionResult> AddStudent(Student stu)
    {
        _context.Add(stu);
        await _context.SaveChangesAsync();
        return Ok();
    }

    [HttpPut("SetHardwareAddress", Name = "SetHardwareAddress")]
    public async Task<IActionResult> SetStudentHardwareAddress(Guid id, string hardwareAddress)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.Id == id);
        if (student == null) return BadRequest("Invalid student id");
        student.DeviceHardwareAddress = hardwareAddress;
        _context.Update(student);
        await _context.SaveChangesAsync();
        return Ok();
    }
    [HttpDelete("DeleteStudent", Name = "DeleteStudent")]
    public async Task<IActionResult> DeleteStudent(Guid id)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.Id == id);
        if (student == null) return BadRequest("Invalid student id");
        _context.Remove(student);
        await _context.SaveChangesAsync();
        return Ok();
    }
    
}