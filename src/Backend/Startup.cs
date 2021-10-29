using KindergartenApi.Context;
using Microsoft.EntityFrameworkCore;
using KindergartenApi.Context;
using Microsoft.EntityFrameworkCore;
using Microsoft.OpenApi.Models;

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

        services.AddControllers();
        services.AddSwaggerGen(c => { c.SwaggerDoc("v1", new() { Title = "KindergartenApi", Version = "v1" }); });
    }

    public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
    {
        // if (app.Environment.IsDevelopment())
// {
        app.UseSwagger();
        app.UseSwaggerUI(c => c.SwaggerEndpoint("/swagger/v1/swagger.json", "KindergartenApi v1"));
// }

// app.UseRouting();

        app.UseCors(m =>
        {
            // m.WithOrigins("https://admin.mineapple.net", "http://localhost:3001", "https://localhost:3001", "https://192.168.1.2:3001",
            //     "http://192.168.1.2:3001");
            m.AllowAnyOrigin();
            m.AllowAnyMethod();
            m.AllowAnyHeader();
        });

        app.UseRouting();
        app.UseAuthorization();

        app.UseEndpoints(endpoints =>
        {
            endpoints.MapControllerRoute(
                "default",
                "api/v{v:apiVersion}/{controller}/{action}/{id?}");

            // endpoints.MapDefaultControllerRoute();
        });
    }
}
