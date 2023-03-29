# CoinExchange
git fetch和git push 的区别，实际上git fetch
git push origin coin-1001
git checkout dev
git merge coin-1001
fast forward
##  禁用掉fast forward
git switch -c coin-1002
git switch -d coin-1001
## BUGIN 分支
假如你现在正在coin-1001上开发自己的TASK，此时你的Monter甩给你了一个bug，任务为issue-100。
很自然地，你想创建一个分支issus-100来修复它，但是，当前在coin-1001上进行的工作还没有提交，并不是你不想提交，而是工作只进行到一半，还没法提交。还没法提交，预计完成还需1天时间。但是，必须在两个小时内修复该bug，怎么办？
可以使用
git stash 将没改完的代码保存起来
git stash list 查看你暂存的代码
git stash pop 恢复并删除你的暂存
git stash apply 恢复但不删除你的暂存记录
