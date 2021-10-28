using KindergartenApi.Models.DB;
using Microsoft.EntityFrameworkCore;

namespace KindergartenApi.Context;

public class GartenContext : DbContext
{
    public DbSet<Student> Children { get; set; } = null!;
    public DbSet<Teacher> Teachers { get; set; } = null!;
    public DbSet<Classroom> Classrooms { get; set; } = null!;

    public GartenContext(DbContextOptions<GartenContext> options) : base(options) {}


    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Teacher>(e =>
        {
            e.HasMany(n => n.Students).WithMany(n => n.Teachers);
        });

        base.OnModelCreating(modelBuilder);
    }
}
