b 2cfb4a6
__f:
cfp
push $ra
lw $al $fp
addi $al 1
lw $a0 $al
push $a0
li $a0 1
lw $rv $a0
sra
addi $sp 1
addi $sp 1
addi $sp 1
sfp
pop
jr $ra
2cfb4a6:
lfp
li $a0 4
push $a0
li $a0 3
push $a0
jal __f
push $a0
cfp
li $a0 4
push $a0
cfp
6e3c1e6:
71f2a7d:
halt
