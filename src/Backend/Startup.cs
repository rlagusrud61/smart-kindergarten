using System.Text.Json;
using System.Text.Json.Serialization;
using KindergartenApi.Context;
using KindergartenApi.Hubs;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi;

public class Startup
{
    private readonly IConfiguration Configuration;

    public Startup(IConfiguration configuration)
    {
        Configuration = configuration;
    }

    public void ConfigureServices(IServiceCollection services)
    {
        services.AddDbContext<GartenContext>(options =>
            options.UseNpgsql(Configuration.GetConnectionString("Postgres")));

        services.AddSwaggerGen(c =>
        {
            c.SwaggerDoc("v1", new()
            {
                Title = "KindergartenApi",
                Version = "v1"
            });
            
            c.DocumentFilter<SwaggerHubTypeFilter>();
        });
        
        // services.AddCors(m =>
        // {
        //     m.AddDefaultPolicy(builder =>
        //     {
        //         builder.AllowAnyHeader();
        //         builder.AllowAnyMethod();
        //         builder.AllowCredentials();
        //         builder.WithOrigins("http://localhost:3000/","https://localhost:3000","https://ss.mineapple.net");
        //     });
        // });
        
        services.AddCors(opts =>
        {
            opts.AddDefaultPolicy(builder =>
            {
                builder.WithOrigins("http://localhost:3000", "https://localhost:3000");
                builder.AllowAnyMethod();
                builder.AllowCredentials();
            });
        });
        
        services.AddControllers()
            .AddJsonOptions(opts =>
            {
                opts.JsonSerializerOptions.Converters.Add(new JsonStringEnumConverter());
                opts.JsonSerializerOptions.PropertyNamingPolicy = JsonNamingPolicy.CamelCase;
                opts.JsonSerializerOptions.PropertyNameCaseInsensitive = true;
            });

        services.AddSignalR()
            .AddJsonProtocol(opts =>
            {
                opts.PayloadSerializerOptions.Converters.Add(new JsonStringEnumConverter());
                opts.PayloadSerializerOptions.PropertyNamingPolicy = JsonNamingPolicy.CamelCase;
                opts.PayloadSerializerOptions.PropertyNameCaseInsensitive = true;
            });
    }

    public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
    {
        app.UseSwagger();
        app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "KindergartenApi v1"));

        
        app.UseRouting();
        
        app.UseCors(m =>
        {
            m.WithOrigins("http://localhost:3000","https://localhost:3000","https://ss.mineapple.net");
            m.AllowAnyMethod();
            m.AllowAnyHeader();
            m.AllowCredentials();
        });


        app.UseAuthorization();

        app.UseEndpoints(endpoints =>
        {
            endpoints.MapControllerRoute(
                "default",
                "api/v{v:apiVersion}/{controller}/{action}/{id?}");

            endpoints.MapHub<ActivityHub>("/Hubs/Activity");
            endpoints.MapHub<StudentHub>("/Hubs/Student/{id}");
        });
    }
}