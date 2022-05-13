!next_move.

algo(wall_follower).
//algo(random_mouse).

// Wall Follower
+!next_move : algo(wall_follower) & 
	not right_cell(obstacle)
	<-
	turn_right;
	move_fwd;
	!next_move.
	
+!next_move : algo(wall_follower) & 
	right_cell(obstacle) & not front_cell(obstacle) 
	<-
	move_fwd;
	!next_move.

+!next_move : algo(wall_follower) & 
	not right_cell(obstacle) 
	<-
	turn_right;
	move_fwd;
	!next_move.

+!next_move : algo(wall_follower) & 
	right_cell(obstacle) & front_cell(obstacle) 
	<-
	turn_left;
	!next_move.

// Random Mouse
+!next_move : algo(random_mouse) & 
	not generated_P &
	(
	(not left_cell(obstacle) & not front_cell(obstacle) & not right_cell(obstacle)) |
	(not left_cell(obstacle) & not front_cell(obstacle)) |
	(not front_cell(obstacle) & not right_cell(obstacle)) |
	(not left_cell(obstacle) & not right_cell(obstacle))
	)
	<-
	.random(P);
	+generated_P;
	!next_move.
	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not front_cell(obstacle) & not right_cell(obstacle))
	& P < 0.33
	<-
	turn_left;
	move_fwd;
	-generated_P;
	!next_move.
	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not front_cell(obstacle) & not right_cell(obstacle))
	& P >= 0.33 & P < 0.66
	<-
	move_fwd;
	-generated_P;
	!next_move.
		
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not front_cell(obstacle) & not right_cell(obstacle))
	& P >= 0.66
	<-
	turn_right;
	move_fwd;
	-generated_P;
	!next_move.
	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not front_cell(obstacle))
	& P < 0.5
	<-
	turn_left;
	move_fwd;
	-generated_P;
	!next_move.
	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not front_cell(obstacle))
	& P >= 0.5
	<-
	move_fwd;
	-generated_P;
	!next_move.

+!next_move : algo(random_mouse) &
	generated_P & 
	(not front_cell(obstacle) & not right_cell(obstacle))
	& P < 0.5
	<-
	move_fwd;
	-generated_P;
	!next_move.
	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not front_cell(obstacle) & not right_cell(obstacle))
	& P >= 0.5
	<-
	turn_right;
	move_fwd;
	-generated_P;
	!next_move.

	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not right_cell(obstacle))
	& P < 0.5
	<-
	turn_left;
	move_fwd;
	-generated_P;
	!next_move.
	
+!next_move : algo(random_mouse) &
	generated_P & 
	(not left_cell(obstacle) & not right_cell(obstacle))
	& P >= 0.5
	<-
	turn_right;
	move_fwd;
	-generated_P;
	!next_move.

+!next_move : algo(random_mouse) &
	not front_cell(obstacle)
	<-
	move_fwd;
	!next_move.

+!next_move : algo(random_mouse) &
	not left_cell(obstacle)
	<-
	turn_left;
	move_fwd;
	!next_move.

+!next_move : algo(random_mouse) &
	not right_cell(obstacle)
	<-
	turn_right;
	move_fwd;
	!next_move.
	
+!next_move : algo(random_mouse)
	<-
	turn_left;
	turn_left;
	move_fwd;
	!next_move.
