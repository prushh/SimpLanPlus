cal
push $al
cfp
b _6e3c1e6_
__f0_:
cal
push $al
cfp
push $ra
cal
addi $al 1
lw $a0 $al
srv
b _2aaf7cc_
_2aaf7cc_:
lw $a0 $sp
sra
pop
lw $a0 $sp
sfp
pop
pop
lrv
jr
_6e3c1e6_:
li $a0 10
push $a0
jal __f0_
print $a0
cal
push $al
cfp
b _71c7db3_
__f1_:
cal
push $al
cfp
push $ra
cal
addi $al 1
lw $a0 $al
push $a0
li $a0 2
lw $t0 $sp
mult $t0 $a0 $a0
pop
srv
b _5a01cca_
_5a01cca_:
lw $a0 $sp
sra
pop
lw $a0 $sp
sfp
pop
pop
lrv
jr
_71c7db3_:
li $a0 10
push $a0
jal __f1_
print $a0
_47f37ef_:
lw $fp $fp
pop
li $a0 10
push $a0
jal __f0_
print $a0
cal
push $al
cfp
b _1153193_
__f2_:
cal
push $al
cfp
push $ra
cal
addi $al 1
lw $a0 $al
push $a0
li $a0 3
lw $t0 $sp
mult $t0 $a0 $a0
pop
srv
b _4563e9a_
_4563e9a_:
lw $a0 $sp
sra
pop
lw $a0 $sp
sfp
pop
pop
lrv
jr
_1153193_:
li $a0 10
push $a0
jal __f2_
print $a0
_19bb089_:
lw $fp $fp
pop
li $a0 10
push $a0
jal __f0_
print $a0
_721e0f4_:
lw $fp $fp
pop
halt
