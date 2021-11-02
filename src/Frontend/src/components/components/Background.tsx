export const Background = ({children} :any) => {
    return (
        // Remove transition-all to disable the background color transition.
        <body className="bg-white dark:bg-black transition-all">
        {children}
        </body>
    )
}
