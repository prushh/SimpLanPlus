f:
cfp $sp $fp
push $ra
lw $fp $sp
li $a0 1
lw $rv $a0
sra $ra $sp
addi $sp 1addi $sp 1sfp $sp $fppopjr $raaddi $sp -1
lw $fp $sp
lfp
push $a0
jal f
push $a0
lw $al $fp
addi $al -1
lw $a0 $al
lw $t0 $sp
pop
sw $t0 $a0
