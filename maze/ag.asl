!next_move.

/* Initial Beliefs */
//algo(wall_follower).
algo(random_mouse).

/* Rules */
left_free :- not left_cell(obstacle) & not left_cell(entrance).
front_free :- not front_cell(obstacle) & not front_cell(entrance).
right_free :- not right_cell(obstacle) & not right_cell(entrance).
left_front_right_free :- left_free & front_free & right_free.
left_front_free :- left_free & front_free & not right_free.
front_right_free :- not left_free & front_free & right_free.
left_right_free :- left_free & not front_free & right_free.


/* Wall Follower */
/* ------------------------------------------------------------------------- */
@p1
+!next_move : algo(wall_follower) & 
	right_free
	<-
	turn_right;
	move_fwd;
	!next_move.

@p2
+!next_move : algo(wall_follower) & 
	not right_free & front_free
	<-
	move_fwd;
	!next_move.

@p3
+!next_move : algo(wall_follower) & 
	not right_free & not front_free
	<-
	turn_left;
	!next_move.
/* ------------------------------------------------------------------------- */






/* Random Mouse */
/* ------------------------------------------------------------------------- */
@p4
+!next_move : algo(random_mouse) & 
	not r(R) & (left_front_right_free | left_front_free | front_right_free | left_right_free)
	<-
	.random(R);
	-+r(R);
	.print(R);
	!next_move.
	
@p5
+!next_move : algo(random_mouse) &
	r(R) & left_front_right_free & R < 1/3
	<-
	.print("R < 1/3, turn_left, move_fwd");
	turn_left;
	move_fwd;
	-r(R);
	!next_move.

@p6
+!next_move : algo(random_mouse) &
	r(R) & left_front_right_free & R >= 1/3 & R < 2/3
	<-
	.print("R >= 1/3 & R < 2/3, move_fwd");
	move_fwd;
	-r(R);
	!next_move.

@p7
+!next_move : algo(random_mouse) &
	r(R) & left_front_right_free & R < 2/3
	<-
	.print("R < 2/3, turn_right, move_fwd");
	turn_right;
	move_fwd;
	-r(R);
	!next_move.

@p8
+!next_move : algo(random_mouse) &
	r(R) & left_front_free & R < 1/2
	<-
	.print("R < 1/2, turn_left, move_fwd");
	turn_left;
	move_fwd;
	-r(R);
	!next_move.

@p9	
+!next_move : algo(random_mouse) &
	r(R) & left_front_free & R >= 1/2
	<-
	.print("R >= 1/2, move_fwd");
	move_fwd;
	-r(R);
	!next_move.

@p10	
+!next_move : algo(random_mouse) &
	r(R) & front_right_free & R < 1/2
	<-
	.print("R < 1/2, move_fwd");
	move_fwd;
	-r(R);
	!next_move.

@p11	
+!next_move : algo(random_mouse) &
	r(R) & front_right_free & R >= 1/2
	<-
	.print("R >= 1/2, turn_right, move_fwd");
	turn_right;
	move_fwd;
	-r(R);
	!next_move.

@p12	
+!next_move : algo(random_mouse) &
	r(R) & left_right_free & R < 1/2 
	<-
	.print("R < 1/2, turn_left, move_fwd");
	turn_left;
	move_fwd;
	-r(R);
	!next_move.

@p13	
+!next_move : algo(random_mouse) &
	r(R) & left_right_free & R >= 1/2
	<-
	.print("R >= 1/2, turn_right, move_fwd");
	turn_right;
	move_fwd;
	-r(R);
	!next_move.

@p14
+!next_move : algo(random_mouse) &
	left_free
	<-
	.print("turn_left, move_fwd");
	turn_left;
	move_fwd;
	!next_move.

@p15
+!next_move : algo(random_mouse) &
	front_free
	<-
	.print("move_fwd");
	move_fwd;
	!next_move.

@p16
+!next_move : algo(random_mouse) &
	right_free
	<-
	.print("turn_right, move_fwd");
	turn_right;
	move_fwd;
	!next_move.

@p17
+!next_move : algo(random_mouse) &
	true
	<-
	.print("turn_left, turn_left, move_fwd");
	turn_left;
	turn_left;
	move_fwd;
	!next_move.
/* ------------------------------------------------------------------------- */
