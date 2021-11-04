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
    private readonly IHubContext<StudentHub, IStudentHub> _studentHub;

    public StudentsController(GartenContext context, HistoryService history,
        IHubContext<StudentHub, IStudentHub> studentHub, IHubContext<ActivityHub, IActivityHub> activityHub)
    {
        _context = context;
        _history = history;
        _studentHub = studentHub;
        _activityHub = activityHub;
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

    /// <summary>
    /// Update a student's current activity
    /// </summary>
    /// <param name="hardwareAddress">The student's own hardware address</param>
    /// <param name="activity"></param>
    /// <returns></returns>
    [HttpPut("Activity")]
    public async Task<IActionResult> UpdateActivity(string hardwareAddress, Activity activity)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == hardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");

        _history.StudentActivity[student.Id] = activity;
        _studentHub.Clients.Group(student.Id.ToString()).ReceiveActivityUpdate(activity);
        return Ok();
    }

    [HttpPut("VocalActivity")]
    public async Task<IActionResult> UpdateVocalActivity(string hardwareAddress, VocalActivity activity)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == hardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");

        _history.StudentVocalActivity[student.Id] = activity;
        _studentHub.Clients.Group(student.Id.ToString()).ReceiveVocalActivityUpdate(activity);
        return Ok();
    }

    [HttpGet("Activity/{hardwareAddress}")]
    public async Task<ActionResult<Activity?>> GetActivity(string hardwareAddress)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == hardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        return Ok(_history.StudentActivity.ContainsKey(student.Id) ? _history.StudentActivity[student.Id] : null);
    }

    [HttpGet("VocalActivity/{hardwareAddress}")]
    public async Task<ActionResult<VocalActivity?>> GetVocalActivity(string hardwareAddress)
    {
        var student = await _context.Children.FirstOrDefaultAsync(m => m.DeviceHardwareAddress == hardwareAddress);
        if (student is null) return BadRequest("The provided hardware address does not belong to a known student");
        return Ok(_history.StudentVocalActivity.ContainsKey(student.Id)
            ? _history.StudentVocalActivity[student.Id]
            : null);
    }
}