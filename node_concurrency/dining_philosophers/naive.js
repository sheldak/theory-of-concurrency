var Fork = function(id) {
    this.state = 0;
    this.id = id;
    return this;
}

var acquire = function(fork, waitingTime, printWaiting, callback) { 
    printWaiting(waitingTime, fork.id);

    if (fork.state == 1) {
        setTimeout(function() {
            acquire(fork, waitingTime*2, printWaiting, callback);
        }, waitingTime);
    }
    else {
        fork.state = 1;
        callback();
    }
}

var release = function(fork, callback) { 
    fork.state = 0;

    callback();
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var id = this.id;

    var printWaiting = function(waitingTime, forkID) {
        console.log("[%d]: Waiting %dms for a fork %d", id, waitingTime, forkID);
    };

    eatLoop(this, printWaiting, count)();
}

var eatLoop = function(philosopher, printWaiting, n) {
    if (n == 0) {
        return function() {};
    } else {
        var fork1 = philosopher.forks[philosopher.f1];
        var fork2 = philosopher.forks[philosopher.f2];
        var id = philosopher.id;

        var nextMeals = eatLoop(philosopher, printWaiting, n-1);

        var releaseSecond = function() {
            release(fork2, nextMeals);
        };

        var releaseFirst = function() {
            release(fork1, releaseSecond);
        };

        var eat = function() {
            console.log("[%d]: I am eating", id);
            releaseFirst();
        };

        var acquireSecond = function() {
            setTimeout(function() {
                acquire(fork2, 1, printWaiting, eat);
            }, 1);
        };

        var acquireFirst = function() {
            setTimeout(function() {
                acquire(fork1, 1, printWaiting, acquireSecond);
            }, 1);
        };
    
        return acquireFirst;
    }
}


var N = 5;
var forks = [];
var philosophers = [];
for (var i = 0; i < N; i++) {
    forks.push(new Fork(i));
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

for (var i = 0; i < N; i++) {
    philosophers[i].startNaive(10);
}