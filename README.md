
# X Company test assignment: MiniChron
You can complete the test in your own time as long as it's within the 2 hour timeframe. You can use any language you like. But, please don't use any existing libraries.

**Important note: config to stdin**

The aim of this exercise is to see how well you can take a spec and implement some working software.

We have a set of tasks, each running at least daily, which are scheduled with a simplified cron. We want to find when each of them will next run.
The scheduler config looks like this:

    30 1 /bin/run_me_daily
    45 * /bin/run_me_hourly
    * * /bin/run_me_every_minute
    * * * 19 /bin/run_me_sixty_times

The first field is the minutes past the hour, the second field is the hour of the day and the third is the command to run. For both cases * means that it should run for all values of that field. In the above example run_me_daily has been set to run at 1:30am every day and run_me_hourly at 45 minutes past the hour every hour. The fields are whitespace separated and each entry is on a separate line.

**We want you to write a command line program that when fed this config to stdin and the simulated 'current time' in the format HH:MM as command line argument outputs the soonest time at which each of the commands will fire and whether it is today or tomorrow.**

When the task should fire at the simulated 'current time' then that is the time you should output, not the next one.
For example given the above examples as input and the simulated 'current time' command-line argument 16:10 the output should be

    1:30 tomorrow - /bin/run_me_daily
    16:45 today - /bin/run_me_hourly
    16:10 today - /bin/run_me_every_minute
    19:00 today - /bin/run_me_sixty_times

We will assess your solution as objectively as possible, to this end we run your solution through a test runner, and then take a look at the source for code clarity.

## Result
Use command line to compile program. When you run the program, it will ask you to enter the current time for calculation. After entering the time, the results for each job will be displayed.

<img width="538" alt="Bildschirm­foto 2023-01-20 um 14 52 45" src="https://user-images.githubusercontent.com/120378890/213688970-b24c4c2d-9bdb-4280-b9b8-526a4b34b303.png">
