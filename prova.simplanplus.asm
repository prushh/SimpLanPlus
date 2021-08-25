addi $hp 1
si 0 $hp
lw $t0 $hp
addi $hp 1
sw $t0 $hp
lw $a0 $hp
push $a0
cfp
lw $al $fp
addi $al 0
lw $al $al
lw $a0 $al
print $a0
75412c2f