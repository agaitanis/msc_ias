/* Initial Beliefs */
algo(wall_follower).
//algo(random_mouse).
//algo(tremaux).

/* Rules */
left_cell_free :- not cell(left,obstacle) & not cell(left,entrance) & not cell(left,marked_twice).
front_cell_free :- not cell(front,obstacle) & not cell(front,entrance) & not cell(front,marked_twice).
right_cell_free :- not cell(right,obstacle) & not cell(right,entrance) & not cell(right,marked_twice).
left_front_right_cell_free :- left_cell_free & front_cell_free & right_cell_free.
left_front_cell_free :- left_cell_free & front_cell_free & not right_cell_free.
front_right_cell_free :- not left_cell_free & front_cell_free & right_cell_free.
left_right_cell_free :- left_cell_free & not front_cell_free & right_cell_free.
in_junction :- left_front_right_cell_free | left_front_cell_free | front_right_cell_free | left_right_cell_free.

/* Initial Plan */
!next_move.

/* Wall Follower */
/* ------------------------------------------------------------------------- */
+!next_move : algo(wall_follower) & 
	right_cell_free
	<-
	turn_right;
	move_fwd;
	!next_move.

+!next_move : algo(wall_follower) & 
	not right_cell_free & front_cell_free
	<-
	move_fwd;
	!next_move.

+!next_move : algo(wall_follower) & 
	not right_cell_free & not front_cell_free
	<-
	turn_left;
	!next_move.
/* ------------------------------------------------------------------------- */






/* Random Mouse */
/* ------------------------------------------------------------------------- */
+!next_move : algo(random_mouse) & 
	not r(R) & in_junction
	<-
	.random(R);
	-+r(R);
	!next_move.
	
+!next_move : algo(random_mouse) &
	r(R) & left_front_right_cell_free & R < 1/3
	<-
	turn_left;
	move_fwd;
	-r(R);
	!next_move.

+!next_move : algo(random_mouse) &
	r(R) & left_front_right_cell_free & R >= 1/3 & R < 2/3
	<-
	move_fwd;
	-r(R);
	!next_move.

+!next_move : algo(random_mouse) &
	r(R) & left_front_right_cell_free & R < 2/3
	<-
	turn_right;
	move_fwd;
	-r(R);
	!next_move.

+!next_move : algo(random_mouse) &
	r(R) & left_front_cell_free & R < 1/2
	<-
	turn_left;
	move_fwd;
	-r(R);
	!next_move.

+!next_move : algo(random_mouse) &
	r(R) & left_front_cell_free & R >= 1/2
	<-
	move_fwd;
	-r(R);
	!next_move.
	
+!next_move : algo(random_mouse) &
	r(R) & front_right_cell_free & R < 1/2
	<-
	move_fwd;
	-r(R);
	!next_move.
	
+!next_move : algo(random_mouse) &
	r(R) & front_right_cell_free & R >= 1/2
	<-
	turn_right;
	move_fwd;
	-r(R);
	!next_move.
	
+!next_move : algo(random_mouse) &
	r(R) & left_right_cell_free & R < 1/2 
	<-
	turn_left;
	move_fwd;
	-r(R);
	!next_move.

+!next_move : algo(random_mouse) &
	r(R) & left_right_cell_free & R >= 1/2
	<-
	turn_right;
	move_fwd;
	-r(R);
	!next_move.

+!next_move : algo(random_mouse) &
	left_cell_free
	<-
	turn_left;
	move_fwd;
	!next_move.

+!next_move : algo(random_mouse) &
	front_cell_free
	<-
	move_fwd;
	!next_move.

+!next_move : algo(random_mouse) &
	right_cell_free
	<-
	turn_right;
	move_fwd;
	!next_move.

// Turn around
+!next_move : algo(random_mouse) &
	true
	<-
	turn_left;
	turn_left;
	move_fwd;
	!next_move.
/* ------------------------------------------------------------------------- */







/* Tremaux */
/* ------------------------------------------------------------------------- */
in_junction_raw :- ((not cell(left,obstacle) & not cell(front,obstacle)) |
					(not cell(front,obstacle) & not cell(right,obstacle)) |
					(not cell(left,obstacle) & not cell(right,obstacle))). 
cnt(0).

+!try_to_mark_back_cell : cnt(CNT) & CNT > 2
	<-
	mark_back_cell;
	-+cnt(0).
	
+!try_to_mark_back_cell : true <- 
	-+cnt(0).

+!move_fwd_and_cnt : cnt(CNT)
	<-
	-+cnt(CNT+1);
	move_fwd.

+!next_move : algo(tremaux) & 
	front_cell_free & not cell(front,marked_once) & in_junction_raw
	<-
	!try_to_mark_back_cell;
	!move_fwd_and_cnt;
	mark_cell;
	!next_move.
	
+!next_move : algo(tremaux) & 
	right_cell_free & not cell(right,marked_once) & in_junction_raw
	<-
	!try_to_mark_back_cell;
	turn_right;
	!move_fwd_and_cnt;
	mark_cell;
	!next_move.
	
+!next_move : algo(tremaux) & 
	left_cell_free & not cell(left,marked_once) & in_junction_raw
	<-
	!try_to_mark_back_cell;
	turn_left;
	!move_fwd_and_cnt;
	mark_cell;
	!next_move.

+!next_move : algo(tremaux) & 
	front_cell_free & cell(front,marked_once)
	<-
	!move_fwd_and_cnt;
	mark_cell;
	!next_move.

+!next_move : algo(tremaux) & 
	right_cell_free & cell(right,marked_once)
	<-
	turn_right;
	!move_fwd_and_cnt;
	mark_cell;
	!next_move.
	
+!next_move : algo(tremaux) & 
	left_cell_free & cell(left,marked_once)
	<-
	turn_left;
	!move_fwd_and_cnt;
	mark_cell;
	!next_move.

+!next_move : algo(tremaux) & 
	front_cell_free
	<-
	!move_fwd_and_cnt;
	!next_move.
	
+!next_move : algo(tremaux) & 
	right_cell_free
	<-
	turn_right;
	!move_fwd_and_cnt;
	!next_move.
	
+!next_move : algo(tremaux) & 
	left_cell_free
	<-
	turn_left;
	!move_fwd_and_cnt;
	!next_move.

// Turn around
+!next_move : algo(tremaux) &
	true
	<-
	turn_right;
	turn_right;
	-+cnt(10);
	!next_move.
/* ------------------------------------------------------------------------- */
