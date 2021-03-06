var hostware = new ActiveXObject( "hostware.global" );
var db = hostware.open( "-in -out alias scheduler" );

drop_table( "SCHEDULER_CLUSTERS"      );
drop_table( "SCHEDULER_HISTORY"       );
drop_table( "SCHEDULER_JOBS"          );
drop_table( "SCHEDULER_JOB_CHAINS"    );
drop_table( "SCHEDULER_JOB_CHAIN_NODES" );
drop_table( "SCHEDULER_ORDER_HISTORY" );
drop_table( "SCHEDULER_ORDER_STEP_HISTORY" );
drop_table( "SCHEDULER_ORDERS"        );
drop_table( "SCHEDULER_TASKS"         );
drop_table( "SCHEDULER_VARIABLES"     );


function drop_table( table_name )
{
    try
    {
        execute( "drop table " + table_name + "; commit" );
    }
    catch( x )
    { 
       WScript.echo( "    " + x.message ); 
       execute( "rollback" ); 
    }
}

function execute( sql )
{
    WScript.echo( sql );
    db.put_line( sql );
}

    