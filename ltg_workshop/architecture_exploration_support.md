**Goal: Model the system architecture to understand more about how it works and what risks may be unique given technical choices**

Start by accessing the system with the ip xxx.xxx.xxx.xxx

**Question: How do I access a remote computer?**

You can use a program called Secure SHell or SSH for short to connect to remote machines. To check if you have this program on your machine, open a terminal and type `ssh` and press enter. If you have it installed you should see an output like this:
```
$ ssh
usage: ssh [-46AaCfGgKkMNnqsTtVvXxYy] [-b bind_address] [-c cipher_spec]
           [-D [bind_address:]port] [-E log_file] [-e escape_char]
           [-F configfile] [-I pkcs11] [-i identity_file]
           [-J [user@]host[:port]] [-L address] [-l login_name] [-m mac_spec]
           [-O ctl_cmd] [-o option] [-p port] [-Q query_option] [-R address]
           [-S ctl_path] [-W host:port] [-w local_tun[:remote_tun]]
           [user@]hostname [command]

```

Now that you see these instructions you can use them to connect!
# TODO: do we need to just give the full ssh command?

**Question: What is running on this computer?**

Ok, so you have access to the computer but now what. A good place to start may be to see what is running on the computer. There are a couple different ways to do this, but a program called `top` can be used to see what processes are using the most CPU which may be a good place to start.

> Note: To exit out of the top program you will need to press `ctrl + c`

**Question: I see a lot of docker things on my machine. But what does that mean?**

Docker is a way to run many different programs on the same computer without them interfering with each other. For example think about someone trying run version 78 of chrome but wanting to also see the newer version 79. This would be impossible without isolation with a tool like docker.

Just like with the other programs, we can understand more about the program by running the name in the terminal and pressing enter. If docker is install you will see output that starts with:
```
$ docker

Usage:  docker [OPTIONS] COMMAND

A self-sufficient runtime for containers
...
```

Now look for a command where you can see what containers are running.

**Question: What are all these things running in docker?**

**Question: These tools look like they do interesting things, how can I see them in a browser?**

**Question: I see a couple of different applications for the website running. How do I know how they work?**
