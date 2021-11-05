export const Background = ({children} :any) => {
    return (
        // Remove transition-all to disable the background color transition.
        <div className="bg-white dark:bg-gray-800 transition-all min-h-screen page-content">
        {children}
        </div>
    )
}
