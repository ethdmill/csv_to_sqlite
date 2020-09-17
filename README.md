# Hello there!

My name is Ethan, and this is my crib -- uh, I mean...my CSV to SQLite Java app.

Essentially, it takes in a CSV file, parses through the data, and if the data is valid, it gets added to a SQLite database. If the data is invalid, it gets put into a new CSV on its own.

Simple enough! Here's how I did it.

## The Process

### Getting Started

The entire project was pretty linear, so I started with the CSV input. I was provided a CSV to use, so I based the program around that. I did not use a Scanner in an effort to keep things nice and tight, but for malleable user input, I would have implemented one that prompts the user for a database file name, an input file, a number of columns, and a delimiter symbol.

Either way, it takes in the file, and then starts parsing through the data. My given CSV file used commas for delimiters, so I split the values by commas and stored the data in an ArrayList.

In true "blink and you'll miss it" fashion, I found that some data *included* commas, so I had to make sure to use a regex to flatten the data with a split before iterating through it. Almost got me there!

### Get That Data Out of Here!

I then implemented a function to parse out the invalid data. The CSV featured ten columns (A-J) that corresponded to what I assumed what generalized user data points. Given the sheer amount of data present in the CSV (over 6000 rows!), I made the assumption that any row without a value stored for each column counted as invalid data, so part of my data-sorting method also checks to see if the data column count per row makes the cut.

If it does, it's valid, and it gets added to an ArrayList, since array lengths in Java don't change. If it's invalid, it gets thrown into a new CSV by way of another method. See you later, you less-than-ten-column disasters! It is also important to mention that I made another assumption: that special characters in column J were supposed to be there, as the data appeared to be location names, and could have been the result of a foreign language.

Though the following statement didn't present an issue until the end, I feel that it's worth mentioning here, since it was at this point that I noticed it -- there was an extra line at the very end of the CSV data: presumably some kind of empty ending string. The invalid data parsing methods didn't pick this up, so we'll come back to it.

### The Next Bit -- Connecting SQLite

The next part is where things got trickier. I have admittedly never worked with SQLite before. I do have MySQL experience, so the concepts weren't totally foreign, but new languages always present their quirks.

I was faced with the task of getting my app to generate a SQLite database, which presented challenges such as "Hey, you need SQLite drivers!" and "Wow, those drivers are in the wrong spot no matter where you put them!", which was initially frustrating.

However, I finally figured out that it was an external project logistics issue -- that is, I had to update the dependencies in my project's build path. Upon doing so, it worked like a charm.

### Table Creation, Data Management, and the Will to Go On

This was it: the home stretch. With some SQLite documentation and YouTube videos by my side, I managed to get a table generation method going, which went very well. The database generated properly! Now I just had to populate it.

Spoiler alert: at the very end of this process, I realized that my program kept adding duplicates upon running every time, so I implemented a "unique" clause to my table creation SQLite query.

Now, it will generate the database once, and upon re-running, will not re-add the data.

### Things Get SQL'd Up

It was at this point that I began to run into a bunch of SQLite errors. Rather, I ran into individual SQLite errors, but when I fixed one, another happened.

However, most of these errors came from the table generation (which I thought worked) and the reading of the data. This is also where that aforementioned final empty character clause came into play.

After resolving errors such as improper connection initialization and the printing of individual column values ten times each, I got it to work. It lives!

### Logistics, or: How I Learned to Stop Worrying and Love SQLite

The program runs nice and smoothly now, and once I figured out how to get SQLite to connect properly, it worked great.

It doesn't take too long to run, and I made an effort to make things as efficient as I could by implementing BufferReader, FileWriters, etc. I also added logs at the very end of the process.

And so, here we are, writing this Readme! 

## Unleashing the Beast (How to Run)

Simply clone the repo, open up the program, and build/run the main class! The database, "bad" data file, and log file will all generate.

## Some Notes

For my own sanity, I included my generated database with the initial commit.

Oh! Not to interrupt myself, but speaking of the initial commit, for some reason, I committed all of the logic at once, which is wildly out of character for me. Repo initialization is usually the first thing I do.

Anyway, I will be **removing** the generated database for future commits. That way, upon cloning the repo, opening the project, and building/running for the first time, the database will be generated right before your very eyes! ...That is, if you're looking at the folder that contains the project. Likewise, I will be removing the "bad" data CSV and the log file, which are both also generated every time the program runs.

I implemented the log functionality last, even after initially writing the first draft of this Readme, and the logs actually helped me to catch something I messed up the first time around. Thanks, log files! It should also be noted that the log method is static, since it only does one specific thing. If I were to need to log other things, I would restructure the log functionality, or even move it to its own class file.

## tl;dr

CSV goes in, SQLite comes out. Now with MORE self-awareness!

## Thank You!

I greatly appreciate you taking the time to look through my project. I hope you like it!