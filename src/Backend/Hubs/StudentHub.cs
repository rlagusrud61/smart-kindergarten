using Microsoft.AspNetCore.SignalR;

namespace KindergartenApi.Hubs;

public interface IStudentHub
{
    
}

public class StudentHub : Hub<IStudentHub>
{
    private readonly ILogger<StudentHub> Logger;
    public StudentHub(ILogger<StudentHub> logger)
    {
        Logger = logger;
    }
    
    public override async Task OnConnectedAsync()
    {
        var httpContext = Context.GetHttpContext();
        if (httpContext?.Request.RouteValues
                .FirstOrDefault(m => m.Key.Equals("id")).Value is not string id)
        {
            Logger.LogInformation("User failed connection to Student Hub, " +
                                  "since they failed to provide an id");
            return;
        }
            
        Logger.LogInformation("User trying to establish connection " +
                              "to Student Hub with id {AppId}", id);
            
        await Groups.AddToGroupAsync(Context.ConnectionId, id);
        await base.OnConnectedAsync();
    }
}