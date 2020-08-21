using System;
using System.IO;

namespace GitHubBranchDelete
{
    class Program
    {

        static async System.Threading.Tasks.Task Main(string[] args)
        {
            //check if we have at least one command line argument given
            if (args.Length != 2)
            {
                System.Console.WriteLine("USAGE: GitHubBranchDelete.exe <list|del> <repolist_text>");
                return;
            }
            else
            {
                var branch_delete = new BranchDelete(args[1]);
                await branch_delete.DeleteBranchesAsync(args[0]);
            }
        }
    }
}