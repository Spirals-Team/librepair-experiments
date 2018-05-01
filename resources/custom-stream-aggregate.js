fromCategory('Test')
    .foreachStream().when(
        {
            $any : function(s,e) {
                linkTo("Test", e);
            }
        })