using KindergartenApi.Context;
using KindergartenApi.Hubs;
using KindergartenApi.Models.DB;
using KindergartenApi.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi.Controllers;

[ApiController]
[Route("/api/[controller]")]
public class StudentsController : ControllerBase
{
    private readonly GartenContext _context;
    private readonly HistoryService _history;
    private readonly IHubContext<ActivityHub, IActivityHub> _activityHub;

    public StudentsController(GartenContext context, HistoryService history)
    {
        _context = context;
        _history = history;
    }

    [HttpGet]
    public ActionResult<IAsyncEnumerable<Student>> GetAllStudents()
    {
        return Ok(_context.Children.OrderBy(m => m.Name).AsAsyncEnumerable());
    }

    // [HttpPost(Name = "AddStudent")]
    // public async Task<IActionResult> AddStudent(Student stu)
    // {
    //     _context.Add(stu);
    //     await _context.SaveChangesAsync();
    //     return Ok();
    // }

    [HttpPut("UpdateActivity")]
    public async Task<IActionResult> UpdateActivity(string ownHardwareAddress, Activity activity)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == ownHardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        
        _history.StudentActivity[student.Id] = activity;
        _activityHub.Clients.All.ReceiveActivityUpdate(student.Id, activity);
        return Ok();
    }
    
    [HttpPut("UpdateVocalActivity")]
    public async Task<IActionResult> UpdateVocalActivity(string ownHardwareAddress, VocalActivity activity)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == ownHardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        
        _history.StudentVocalActivity[student.Id] = activity;
        _activityHub.Clients.All.ReceiveVocalActivityUpdate(student.Id, activity);
        return Ok();
    }
    
    [HttpGet("Activity")]
    public async Task<ActionResult<Activity?>> GetActivity(string ownHardwareAddress)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == ownHardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        return Ok(_history.StudentActivity.ContainsKey(student.Id) ? _history.StudentActivity[student.Id] : null);
    }
    
    [HttpGet("VocalActivity")]
    public async Task<ActionResult<VocalActivity?>> GetVocalActivity(string ownHardwareAddress)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == ownHardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        return Ok(_history.StudentVocalActivity.ContainsKey(student.Id) ? _history.StudentVocalActivity[student.Id] : null);
    }
    
}