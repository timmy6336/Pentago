Without alpha/beta pruning at a depth of 4 it took 253ms and expanded 91045 nodes for it to make the first move
(which has the most choices so is the longest)
with alpha/beta pruning at a depth of 4 it took 61ms and expanded 5621 nodes

Without alpha/beta pruning at a depth of 6 it took 47.5 seconds and expanded 24766885 nodes for it to make the first move
(which has the most choices so is the longest)
with alpha/beta pruning at a depth of 6 it took 2.1 seconds and expanded 621255 nodes

BONUS

At a depth of 8 it took 2 minutes for it to make a move WITH alpha/beta pruning and expanded 37309378 nodes
but without alpha/beta pruning i left it running for over an hour without it making a move.


space complexity would be about O(32*n) where n is the depth, 32 is the max brancing factor since the first move has 32 possible placements
it would be a decreasing amount of placements and every other depth would have a branching factor of 8 for the possible rotations.

time complexity would then be O(32^n)

with alpha beta pruning at depth 4 the time complexity was 1/4 of it without and space was 1/16

at depth 6 the time complexity was 1/22 that of it without Alpha Beta and space was 1/40. 



i believe my value function is fairly good, it puts all possible combinations of wins into there own arrays.
if in any of those arrays a win is possible for one of those players it will give that array a value equal
to 5^n where n is the number of tiles of the possible winner in that row, if the max player can win the value
will be positive if its the min then itll be negitive, if nither players can win in that row ie both players
have pieces in it it will have a value of 0. after a value is assigned to each possible win it will then
sum up every one.  i believe this is good because if say the max player had 4 pieces in a row it will 
encourage the min player to block it because the value for that row will go from 625 to zero which is a large 
reduction where as if there was only 2 pieces in a row the value would go from 25 to zero which isnt as good. 
i did have success with the ai winning but it wasnt consistant because i think i might have messed something up
while implementing the minimax, since both a placement and rotation have to be taken into consideration i treated
them as diffrent moves, however if a winnig board was reach it would no longer expand and the value was set really high or 
low for that one. not sure how i could have improved it but didnt have the time unfortinatly.