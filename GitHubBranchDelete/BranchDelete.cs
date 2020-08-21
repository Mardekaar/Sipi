using System;
using System.Net;
using System.IO;
using System.Collections.Generic;
using Octokit;
using System.Threading.Tasks;
using System.Linq;
using System.Text.RegularExpressions;

namespace GitHubBranchDelete
{
    public class BranchDelete
    {
        private string RepoList_fname;
        private string token;
        private readonly string[] DefaultBranches = new string[] { "live", "live-sxs", "master", "master-sxs", "loc-release", "loc-release-sxs" };

        public string GitUrl(string URL, string r_type)
        {
            switch (r_type)
            {
                case "owner":
                    return Regex.Match(URL, @"https:\/\/github\.com\/(\w-\w+|\w+)\/(.*)").Groups[1].Value;
                case "repo":
                    return Regex.Match(URL, @"https:\/\/github\.com\/(\w-\w+|\w+)\/(.*)").Groups[2].Value;
                default:
                    return "error";
            }
        }

        public BranchDelete(string fname) // constructor, sets RepoList and the token
        {
            if (File.Exists(fname))
            {
                RepoList_fname = fname;
                using (var reader = File.OpenText("d:\\Downloads\\token.txt"))
                {
                    token = reader.ReadLine();
                }
            }
            else
            {
                throw new ArgumentException($"Provided file does not exist", fname);
            }
        }

        internal async Task DeleteBranchesAsync(string ToDo)
        {
            var productInfo = new ProductHeaderValue("BranchDelete");
            var credentials = new Credentials(this.token);
            var client = new GitHubClient(productInfo) { Credentials = credentials };

            using (var RepoList_file = File.OpenText(RepoList_fname))
            {
                if (ToDo == "list")
                {
                    var logfile_name = Path.GetDirectoryName(RepoList_fname) + "\\branchlist_" + Path.GetFileName(RepoList_fname);
                    using (var logfile = File.CreateText(logfile_name))
                    {
                        var log_entry = "";
                        var URL_name = RepoList_file.ReadLine();
                        while (URL_name != null)
                        {
                            if (!URL_name.StartsWith(@"https://github.com/"))
                            {
                                log_entry = "ERROR: invalid URL " + URL_name;
                                logfile.WriteLine(log_entry);
                            }
                            else
                            {
                                {
                                    var owner_name = GitUrl(URL_name, "owner");
                                    var repo_name = GitUrl(URL_name, "repo");
                                    try
                                    {
                                        var branches = await client.Repository.Branch.GetAll(owner_name, repo_name);
                                        foreach (var branch in branches)
                                        {
                                            if (DefaultBranches.FirstOrDefault(stringToCheck => stringToCheck.Contains(branch.Name)) == null)
                                            {
                                                log_entry = URL_name + "/" + branch.Name;
                                                logfile.WriteLine(log_entry);
                                            }
                                        }
                                    }
                                    catch (Exception)
                                    {
                                        log_entry = "ERROR: repo not found " + URL_name;
                                        logfile.WriteLine(log_entry);
                                        return;
                                    }
                                }

                            }
                            URL_name = RepoList_file.ReadLine();
                        }
                    }
                }
                else if (ToDo == "del")
                {
                    var URL_name = RepoList_file.ReadLine();
                    while (URL_name != null)
                    {
                        var owner_name = GitUrl(URL_name, "owner");
                        var repo_name = GitUrl(URL_name, "repo");
                        try
                        {
                            var branches = await client.Repository.Branch.GetAll(owner_name, repo_name);
                            foreach (var branch in branches)
                            {
                                if (DefaultBranches.FirstOrDefault(stringToCheck => stringToCheck.Contains(branch.Name)) == null)
                                {
                                    //delete branch
                                }
                            }
                        }
                        catch (Exception)
                        {
                            System.Console.WriteLine($"Could not delete a branch from {repo_name}.");
                            return;
                        }
                    }
                }
                else
                {
                    System.Console.WriteLine("USAGE: GitHubBranchDelete.exe <list|del> <repolist_text>");
                    return;
                }
            }
        }
    }
}