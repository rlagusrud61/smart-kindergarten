using KindergartenApi.Hubs;
using KindergartenApi.Models.DB;
using KindergartenApi.Models.DB.Activity;
using KindergartenApi.Models.DTO;
using Microsoft.AspNetCore.SignalR;

namespace KindergartenApi.Services;

public class UrgentEventService
{
    private readonly ILogger<UrgentEventService> Logger;
    private readonly IHubContext<ActivityHub, IActivityHub> _activityHub;
    private readonly HistoryService _historyService;

    public UrgentEventService(IHubContext<ActivityHub, IActivityHub> activityHub, HistoryService historyService,
        ILogger<UrgentEventService> logger)
    {
        _activityHub = activityHub;
        _historyService = historyService;
        Logger = logger;
    }

    public async Task OnActivityUpdateAsync(Student student)
    {
        var studentId = student.Id;
        if (!_historyService.StudentActivity.ContainsKey(studentId) ||
            !_historyService.StudentVocalActivity.ContainsKey(studentId)) return;

        var activity = _historyService.StudentActivity[studentId];
        var vocal = _historyService.StudentVocalActivity[studentId];

        var urgentEvent = GetEventForActivities(activity, vocal);
        if (urgentEvent is null) return;

        var lastDate = GetDateFromDictionary(studentId, urgentEvent.Value);
        var validEvent =
            lastDate is null ||
            DateTime.UtcNow - lastDate > TimeSpan.FromMinutes(5); // Can't repeat an event within 5 minutes

        if (!validEvent) return;

        // Update history and send event to clients
        _historyService.RecentUrgentEvents[studentId][urgentEvent.Value] = DateTime.UtcNow;
        await _activityHub.Clients.All.ReceiveUrgentEvent(
            new EventHistory { Student = student, Date = DateTime.UtcNow, Event = urgentEvent.Value });
    }

    /**
     * Gets the date from the dictionary, and adds any missing dictionary entries if necessary
     */
    private DateTime? GetDateFromDictionary(Guid studentId, UrgentEvent urgentEvent)
    {
        if (!_historyService.RecentUrgentEvents.ContainsKey(studentId))
        {
            _historyService.RecentUrgentEvents.Add(studentId, new Dictionary<UrgentEvent, DateTime>());
            return null;
        }

        var recentEvents = _historyService.RecentUrgentEvents[studentId];

        if (recentEvents.ContainsKey(urgentEvent)) return recentEvents[urgentEvent];

        recentEvents.Add(urgentEvent, DateTime.UtcNow);
        return null;
    }

    private static UrgentEvent? GetEventForActivities(Activity activity, VocalActivity vocalActivity)
    {
        return (activity, vocalActivity) switch
        {
            (Activity.Falling, _) => UrgentEvent.Falling,
            (Activity.Fighting, not VocalActivity.Laughing) => UrgentEvent.Fighting,
            (_, VocalActivity.Crying) => UrgentEvent.Crying,
            _ => null
        };
    }
}