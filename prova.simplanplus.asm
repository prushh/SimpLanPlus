cal
push $al
cfp
b _357246d_
__f0_:
cal
push $al
cfp
push $ra
cal
addi $al 1
lw $a0 $al
push $a0
li $a0 10
lw $t0 $sp
add $t0 $a0 $a0
pop
push $a0
cal
addi $al 1
lal
lw $t0 $sp
pop
sw $t0 $a0
li $a0 1
srv
b _6adca53_
_6adca53_:
lw $a0 $sp
sra
pop
lw $a0 $sp
sfp
pop
pop
lrv
jr
_357246d_:
addi $hp 1
si 0 $hp
lhp
push $a0
li $a0 50
push $a0
cal
addi $al -2
lw $a0 $al
push $a0
cal
addi $al -1
lw $al $al
lal
lw $t0 $sp
pop
sw $t0 $a0
cal
addi $al -2
lw $a0 $al
push $a0
jal __f0_
cal
addi $al -2
lw $a0 $al
print $a0
_725bef6_:
pop
pop
lw $fp $fp
pop
halt
