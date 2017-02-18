1.org.quartz.impl.jdbcjobstore.DriverDelegate
	增加方法：
	String selectJobClassForTrigger(Connection conn, String triggerName,String groupName) throws SQLException;
	
	
2.org.quartz.impl.jdbcjobstore.StdJDBCDelegate
	增加方法：
	String selectJobClassForTrigger(Connection conn, String triggerName,String groupName) throws SQLException{。。。。}
	
	
3.org.quartz.impl.jdbcjobstore.JobStoreSupport
	增加：
	protected RecoverMisfiredJobsResult recoverMisfiredJobs(Connection conn, boolean recovering)
	在上面方法中增加
	==>
	List tempMisfiredTriggers = new ArrayList();
        for (Iterator misfiredTriggerIter = misfiredTriggers.iterator(); misfiredTriggerIter.hasNext();) {
            Key triggerKey = (Key) misfiredTriggerIter.next();
            String jobClass = getDelegate().selectJobClassForTrigger(conn, triggerKey.getName(), triggerKey.getGroup());
            try {
				getClassLoadHelper().loadClass(jobClass);
			} catch (ClassNotFoundException e) {
				tempMisfiredTriggers.add(triggerKey);
				continue;
			}
      }
	
	增加：
	protected Trigger acquireNextTrigger(Connection conn, SchedulingContext ctxt, long noLaterThan)
	在上面方法中增加
	==>
	String jobClass = getDelegate().selectJobClassForTrigger(conn, triggerKey.getName(), triggerKey.getGroup());
    try {
		getClassLoadHelper().loadClass(jobClass);
	} catch (ClassNotFoundException e) {
		continue;
	}
