alter table goal_table
add root integer not null default -1;

with recursive get_parents(id, gid, uid, root, title) as (
    select goal_tab.id, goal_tab.gid, goal_tab.uid, goal_tab2.id as root, goal_tab.title
    from goal_table goal_tab
             left join goal_table goal_tab2 on goal_tab.id = goal_tab2.id
    where goal_tab.gid is null and goal_tab.uid = 15
    union all
    select gt.id, gt.gid, gt.uid, gp.root, gt.title
    from get_parents gp, goal_table gt
    where gt.gid = gp.id
)
update goal_table gt set root = gp.root
from get_parents gp where gt.id = gp.id;

